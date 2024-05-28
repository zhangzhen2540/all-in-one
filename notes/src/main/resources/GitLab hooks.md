# GitLab hooks

gitlab hooks 分为客户端 hook 与服务端 hook,


## Gitlab 客户端 hook
客户端 hook 在客户机器上, 如果是单项目的 hook 配置, 目录位于 workdir/.git/hooks/
workdir/.git/hooks 目录下默认创建了 .sample 结尾的实例文件

全局配置则可以通过 git config --global -l 来查看:
```
init.templatedir=~/.git-templates
core.hookspath=~/.git-templates/hooks
```


通过 git config --global core.hooksPath '~/.git-templates/hooks' 来配置全局 hook 的目录
配置完成后也可以通过 cat ~/.gitconfig 来查看最新配置


### pre-commit
	pre-commit 在提交前做一些动作

	如果想要绕过本地 pre-commit hooks, 可以在 git commit -am 'msg' --no-verify 通过 --no-verify 来忽略


以下是一个 pre-commit 文件,用来在提交前检查提交 pom 的版本号是否符合指定规则
(将内容写入 pre-commit 文件, 然后将文件移动到 workdir/.git/hooks 或 ~/.git-templates/hooks 目录下, 并且将文件设置为可执行, git提交前会自动调用该脚本, 如果脚本返回非0, 则会拒绝提交)

```
#!/bin/bash

cur_branch=$(git rev-parse --abbrev-ref HEAD)


declare -A map
map["dev"]="0\.0\.1-SNAPSHOT"
map["stable"]="0\.0\.2-SNAPSHOT"
map["test"]="0\.0\.4-SNAPSHOT"
map["main"]="^.*\.RELEASE$"
map["master"]="^.*\.RELEASE$"
# echo ${map[@]}


for key in ${!map[*]}; do
    if [[ $key == $cur_branch ]]; then
        exp_version=${map[$key]}
    fi
done

# echo "current branch: $cur_branch, expect version: $exp_version"
if [[ ${#exp_version} -gt 0 ]]; then
    echo "current branch: $cur_branch, expect version: $exp_version"
else
    echo "No expect version"
    exit 0
fi

STAGE_FILES=$(git diff --cached --name-only -- '*pom.xml')
if test ${#STAGE_FILES} -gt 0
then
    echo '开始pom.xml版本号检查'
    for FILE in $STAGE_FILES
    do
#        echo $FILE
        if [[ $FILE == "pom.xml"  ]]; then
            result=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' $FILE)  
        else
            result=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="parent"]/*[local-name()="version"]/text()' $FILE)
        fi       
#        result=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' $FILE)
        echo $result | grep -E $exp_version
        if [[ "$?" == 0 ]]; then
#        	echo "分支版本符合预期, 文件: $FILE 期望版本: $exp_version, 实际版本: $result"
            echo "OK"
        else
        	echo "分支版本不符合预期, 文件: $FILE, 期望版本格式: $exp_version, 实际版本: $result"
      		PASS=false
      		exit 1
        fi
    done
  echo "pom.xml版本号检查完毕"
else
    echo '没有pom.xml文件需要检查'
fi

exit 0
```



## Gitlab 服务端 hook

服务端 hook 位于 gitlab 服务器上


1. 本地通过 docker 创建 gitlab 服务器
   gitlab.zz.com 是本地dns文件映射的域名
```
sudo docker run --detach \
  --hostname gitlab.zz.com \
  --publish 8843:443 --publish 8088:80 --publish 8822:22 \
  --name gitlab2 \
  --restart always \
  --volume /home/zz/data/gitlab/config:/etc/gitlab \
  --volume /home/zz/data/gitlab/logs:/var/log/gitlab \
  --volume /home/zz/data/gitlab/data:/var/opt/gitlab \
  gitlab/gitlab-ce:11.11.0-ce.0
```


2. 进入 docker gitlab2 容器
   编辑 /etc/gitlab/gitlab.rb 文件, 指定 hooks 位置
   gitlab_shell['custom_hooks_dir'] = "/etc/gitlab/hooks"


	我们需要创建 pre-receive hook, 在 /etc/gitlab/hooks 目录下创建 pre-receive.d 目录, 然后在该目录下写入 pre-receive 文件


3. 编写 pre-receive 文件

   git diff-tree 与 ls-tree: ls-tree 可以列出 commit-id 的所有文件内容, diff-tree 则是比较两次提交间的差异, 由于我们只需要校验最后的pom文件版本格式, 所以直接用 ls-tree 来查看最后一次提交的文件

```
#!/bin/bash


declare -A map
map["dev"]="0\.0\.1-SNAPSHOT"
map["stable"]="0\.0\.2-SNAPSHOT"
map["test"]="0\.0\.4-SNAPSHOT"
map["main"]="^.*\.RELEASE$"
map["master"]="^.*\.RELEASE$"


validate_version()
{

    # --- Arguments
    oldrev=$(git rev-parse $1)
    newrev=$(git rev-parse $2)
    refname="$3"

    # init branch push
    if [[ $oldrev == '0000000000000000000000000000000000000000' ]]; then
        init=1
#        exit 0
    fi

    # delete branch
    if [[ $newrev == '0000000000000000000000000000000000000000' ]]; then
        exit 0
    fi

    echo ">>>>>>>>>>>>>>>>>>>>>"
    echo $refname: $oldrev, ~, $newrev, $init
    current_branch=$refname
    short_current_branch="$(echo $current_branch | sed 's/refs\/heads\///g')"
    echo $short_current_branch
    echo "<<<<<<<<<<<<<<<<<<<<<"

    for key in ${!map[*]}; do
        if [[ $key == $short_current_branch ]]; then
            exp_version=${map[$key]}
        fi
    done

    # echo "current branch: $cur_branch, expect version: $exp_version"
    if [[ ${#exp_version} -gt 0 ]]; then
        echo "current branch: $short_current_branch, expect version: $exp_version"
    else
        echo "No check version"
        exit 0
    fi

    init=1
    if [[ $init == 1 ]]; then
        echo "init >>>>>>>>>>>>>>>>>>>>>." 
        commitList=($newrev)
        split=($newrev)
    else 
        commitList=`git rev-list $oldrev..$newrev`
        split=($commitList)
    fi
#    echo $commitList

#    split=($commitList)
    for s in ${split[@]}
    do

#        STAGE_FILES=$(git diff-tree --no-commit-id --name-only -r $s | grep 'pom\.xml$')
        STAGE_FILES=$(git ls-tree --name-only -r $s | grep 'pom\.xml$')

        echo "files: $STAGE_FILES"
        if test ${#STAGE_FILES} -gt 0
        then
            echo '开始pom.xml版本号检查'

            for FILE in $STAGE_FILES
            do
                if [[ $FILE == "pom.xml"  ]]; then
                    result=$(git show $s:$FILE | xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' -)  
                else
                    result=$(git show $s:$FILE | xmllint --xpath '/*[local-name()="project"]/*[local-name()="parent"]/*[local-name()="version"]/text()' -)
                fi       
                echo $result | grep -E $exp_version
                if [[ "$?" == 0 ]]; then
        #           echo "分支版本符合预期, 文件: $FILE 期望版本: $exp_version, 实际版本: $result"
                    echo "OK"
                else
                    echo "分支$cur_branch版本不符合预期, 文件: $FILE, 期望版本格式: $exp_version, 实际版本: $result"
                    PASS=false
                    exit 1
                fi
            done
          echo "pom.xml版本号检查完毕"
        else
            echo '没有pom.xml文件需要检查'
        fi
    done
    exit 0
}

fail=""

# Allow dual mode: run from the command line just like the update hook, or
# if no arguments are given then run as a hook script
if [ -n "$1" -a -n "$2" -a -n "$3" ]; then
    # Output to the terminal in command line mode - if someone wanted to
    # resend an email; they could redirect the output to sendmail
    # themselves
    PAGER= validate_version $2 $3 $1
else
    while read oldrev newrev refname
    do
        validate_version $oldrev $newrev $refname
    done
fi

if [ -n "$fail" ]; then
    exit $fail
fi

```
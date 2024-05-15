## K8S 命令


kubectl describe pod seata-server-8456ff75b8-d92bd -n stable

kubectl logs -f seata-server-8456ff75b8-d92bd -n stable




kubectl -n stable exec -it zookeeper-855769cd48-tdgnr bash


1.kubectl cp /主机目录/文件路径 podName:/容器路径/xxx.datasource -n namespaces
这样可以把主机目录文件拷贝到容器内

2.kubectl cp podName:容器路径/xxx.datasource -n namespaces /主机目录
这样可以把容器内文件cp到主机目录




## k8s pod 中复制文件
	
### pod 内文件复制到 k8s-master-1
	kubectl exec -n dev finance-service-dev-0 -- tar cf - /data/fin-dev-heap.bin  | tar xf - -C /root/tmp/

### k8s-master-1 复制到 da156.nexus.cn
	k8s-master-1 用户 zz-pc/Dayi@2022
	可scp复制文件到 da156.nexus.cn

### dump 内存
	jmap –dump:live,format=b,file=heap.bin <pid>
	
### ha457 分析dump文件
	java -jar -Xmx3000m ha457.jar










## 复制文件到k8s-master-1

	scp -P 10022 zk-config.sh root@172.26.252.42:~
	Hinimix@2019


	172.26.252.42 (k8s-master-1 ip)







## minikube
	minikube dashboard


	minikube start

	 kubectl get services -n dev
	 kubectl get svc

	 kubectl describe svc -n dev seata-server




	minikube tunnel
	https://www.jtr109.com/posts/expose-minikube-services-out-of-host/


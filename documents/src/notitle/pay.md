# 支付

## ## 流程

TradeDomainService#trade(TradeContext)  交易业务处理
    开关检查
    记账（业务流水）
    交易

TradeValidateRule 交易业务校验


PayDomainService#pay(PayContext)    单笔支付处理
    记账（底层流水）
    支付/查询
    处理结果
    

TradeContext 一次业务交易上下文
PayContext 一次支付上下文

TradeProcessExtPt 提供交易不同场景、不同环境提供扩展点
ChannelRuleExtPt  提供不同支付通道扩展


交易开关：
    关闭后不处理新交易请求

业务记账：
    TradeProcessExtPt.fill 
    TradeProcessExtPt.check

交易：
    TradeProcessExtPt.start
    TradeProcessExtPt.getAccountTransfer
    TradeValidateRule.checkTrade
    获取到支付账户后，循环处理单笔支付：
        PayDomainService.pay
    TradeValidateRule.checkTrade
    TradeProcessExtPt.success/fail



TradeFinishDomainService    完成交易处理
    TradeValidateRule.checkFinish
    BusinessStatementEntity.finish      创建对手账单
    TradeProcessExtPt.preFinish
    BusinessStatementEntity.markComplete
    TradeProcessExtPt.postFinish



TradeContext#init       恢复交易上下文
    TradeProcessExtPt.initExt



AbstractTradeFunction



insert into fin_virtual_account_cancel(
id, company_id, main_account_id, main_account_user_name, main_account_no,
 account_host_no, account_no, account_name, 
 customer_cert_type, customer_cert_no, type, ower_type, ower_id, create_time, canceled)
select 
 id, company_id, main_account_id, main_account_user_name, main_account_no,
 account_host_no, account_no, account_name, 
 customer_cert_type, customer_cert_no, type, ower_type, ower_id, create_time, false
from fin_virtual_account
where 



IPayDomainService(PayContext)

CiticDomainService(PayContext)

中信

工行

商品

汇票





## 关注点

### CostType


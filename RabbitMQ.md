# RabbitMQ

## 关于 confirm 及 return 回调
如果队列长度被限制，confirm 仍然会被触发

正确的交换器以及路由键，confirm 被回调

不存在的交换器，return 被回调

交换器存在，但是消息无法路由到队列，confirm 被触发，ack=true，return 被触发

如果通道关闭太快，可能无法正确触发 confirm 回调
```
ps: 所以在使用 confirm 模式确保消息不丢失时，即使消息投递成功，发送端也可能无法得知该状态，导致消息重复发送，需要在消费端考虑消息消费的幂等性
```

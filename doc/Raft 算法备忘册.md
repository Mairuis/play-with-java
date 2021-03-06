# Raft 算法备忘册

## 名词与角色

- 多数派 Majority 或 Quorum 为 (集群机器数 / 2) + 1
- 领导者 Leader 整个集群中只有一个，唯一的事务处理者
- 追随者 Follower 可以有多个，收到事务请求会转发给 Leader
- 观察者 Observer 不参与任何投票和竞选，只负责记录日志
- 候选人 Candidate 由心跳超时后的 Follower 转变而来
- 周期 Term / Epoch 一个 Leader 的唯一周期号，如果 Leader 发生变化，新的 Leader 会得到新的Id
- 事务Id TransactionId / Txid

## 需要注意的几点

- Quorum 由集群的机器数量而不是在线服务器的数量计算得出，如5台服务器其中三台离线，剩下在线的两台无法组成Quorum，因为 (5 / 2) + 1 = 3
- 因为每个事务需要 Quorum 中所有机器通过才能提交，所以无论集群中哪台机器下线或者机器下线都不会导致数据丢失，因为总有一台之前属于Quorum的机器参与竞选并胜出（竞选Leader时，事务Id大的机器优先），论文中称此为重叠性。回到5台机器的例子，其中3台下线，剩余的两台不足以组成 Quorum 所以不会处理写请求，当2台下线时在线的集群中一定有一台属于上一个 Epoch 的Quorum机器，并且在竞选中胜出，然后将自己的数据同步给Quorum中其他机器
- 当心跳超时变为 Candidate 角色时，选举时间一定要每重置一次 Term 都重新随机一个新的时间，避免两个服务器随机到同一随机数用做超时时间，这样永远也选不出 Leader
- 在投票给对方时，先确保对方 Term 大于等于自己，如果等于还需判断对方 Txid 是否大于等于自己
- Leader 发送 Txid 为 N 的请求给 Follower 时需要带上第 N - 1 个请求，当 Follower 第 N 个请求时需要验证自己已经收到的第 N - 1 个请求和 Leader 一致。
如果不一致需要 Leader 再发送第 N - 1 及 N - 2 的请求，Follower 写入 N - 1 时再先验证 N - 2 是否与自己的 N - 2 一致，重复直到遇到 N - N 与自己一致为止。详细见6.824的26节《fault tolerance-raft- 2-1》

##实现技巧

- 为每个 Follower 准备一个队列，记录该 Follower 的 Last Txid ，根据该Id同步数据
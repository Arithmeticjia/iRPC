### RPC远程过程调用

主要用于解决分布式系统中，服务之间的调用问题。远程调用时，要能够像本地调用一样方便，让调用者感知不到远程调用的逻辑
例如：在实际后台开发中，开发者A负责数据查询服务，开发者B负责用户逻辑服务，那么开B需要调用A的用户数据查询服务，所以A.B需要有一个共同约定协调好的接口，A实现该接口，B调用该接口

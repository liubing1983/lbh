package com.lb.hadoop.zk.demo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.WatchedEvent;

/**
 *  测试zk基础功能
 * Created by lb on 2014/10/28.
 */
public class TestZK {

    // session超时时间
    private static final int SESSION_TIMEOUT = 30000;

    ZooKeeper zk;

    Watcher wh = new Watcher() {
        public void process(WatchedEvent event) {
            System.out.println(event.toString());
        }
    };

    private void createZKInstance() throws IOException {
        zk = new ZooKeeper("10.21.3.42:2181", TestZK.SESSION_TIMEOUT, this.wh);
    }

    private void ZKOperations() throws KeeperException, InterruptedException {
        //zk.delete("/lb2", -1);
        System.out.println("\n1. 创建ZooKeeper节点");

        zk.create("/lb2", "date2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("查看是否创建成功");
        System.out.println(new  String(zk.getData("/lb2", false, null)));

        zk.setData("/lb2", "update".getBytes(), -1);
        System.out.println(new  String(zk.getData("/lb2", false, null)));

        zk.delete("/lb2", -1);

        System.out.println(zk.exists("/lb2", false));


    }

    private void ZKClose() throws InterruptedException{
        zk.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException{
        TestZK t = new TestZK();
        t.createZKInstance();
        t.ZKOperations();
        t.ZKClose();
    }

}


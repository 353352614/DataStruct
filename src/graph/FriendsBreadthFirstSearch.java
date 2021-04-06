package graph;

import java.util.*;

/** 广度优先遍历获取二度好友关系
 * @author dengwenqi
 */
public class FriendsBreadthFirstSearch {
    /**
     * @param userNodes 表示整个图的结点数组 Node[]。由于每个用户使用 user_id 来表示，
     *                  所以我可以使用连续的数组表示所有的用户。用户的 user_id 就是数组的下标。
     * @param userId    需要进行BFS的用户
     */
    public void breadthFirstSearch(Node[] userNodes, int userId) {
        //防止数据越界
        if (userId >= userNodes.length - 1) {
            return;
        }
        //BFS依靠队列
        Queue<Integer> queue = new LinkedList<>();
        //初始用户进队列
        queue.offer(userId);
        //存放已经访问过的节点，防止回路
        HashSet<Integer> visitedNodes = new HashSet<>();
        visitedNodes.add(userId);
        //队列的首节点
        int currentUserId;
        while (!queue.isEmpty()) {
            currentUserId = queue.poll();
            if (null == userNodes[currentUserId]) {
                continue;
            }
            Iterator<Integer> iterator =userNodes[currentUserId].friends.stream().iterator();
            while(iterator.hasNext()){
                int friendsId = iterator.next();
                if (null == userNodes[friendsId]) {
                    continue;
                }
                if (visitedNodes.contains(friendsId)) {
                    continue;
                }
                queue.offer(friendsId);
                visitedNodes.add(friendsId);
                userNodes[friendsId].degree = userNodes[currentUserId].degree + 1;
                System.out.printf("\t%d度好友：%d%n", userNodes[friendsId].degree, friendsId);
        }
            }
    }

    public static void main(String[] args) {
        int nodeSum = 11;
        Node[] userNodes = new Node[nodeSum];
        for (int i = 0; i < userNodes.length; i++) {
            userNodes[i] = new Node(i);
        }
        int relationNum = 20;
        Random random = new Random();
        for (int i = 0; i < relationNum; i++) {
            Node userNodeA = userNodes[random.nextInt(nodeSum)];
            Node userNodeB = userNodes[random.nextInt(nodeSum)];
            if(userNodeA.userId == userNodeB.userId) {continue;}
            userNodeA.friends.add(userNodeB.userId);
            userNodeB.friends.add(userNodeA.userId);
        }

        new FriendsBreadthFirstSearch().breadthFirstSearch(userNodes,0);
    }

}

class Node {
    /**
     * 结点的名称，这里使用用户id
     */
    public int userId;
    /**
     * 使用哈希映射存放相连的朋友结点。哈希便于确认和某个用户是否相连。
     */
    public HashSet<Integer> friends;

    /**
     * 用于存放和给定的用户结点，是几度好友
     */
    public int degree;

    /**
     * 初始化结点
     */
    public Node(int id) {
        userId = id;
        friends = new HashSet<>();
        degree = 0;
    }
}
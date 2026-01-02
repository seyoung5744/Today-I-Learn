package kuke.board.common.outboxmessagerelay;

import lombok.Getter;

import java.util.List;
import java.util.stream.LongStream;

/**
 * shard를 각 애플리케이션에 균등하게 할당하기 위한 AssignedShard
 */
@Getter
public class AssignedShard {

    // 해당 애플리케이션에 할당된 shard 번호들
    private List<Long> shards;

    /**
     * @param appId      현재 실행되고 있는 애플리케이션 ID
     * @param appIds     coordinator 에 의해 현재 실행되고 있는 애플리케이션 목록
     * @param shardCount Shard 개수
     */
    public static AssignedShard of(String appId, List<String> appIds, long shardCount) {
        AssignedShard assignedShard = new AssignedShard();
        assignedShard.shards = assign(appId, appIds, shardCount);
        return assignedShard;
    }

    private static List<Long> assign(String appId, List<String> appIds, long shardCount) {
        int appIndex = findAppIndex(appId, appIds);
        if (appIndex == -1) { // 할당할 shard 가 없으므로 빈 list 반환
            return List.of();
        }

        long start = appIndex * shardCount / appIds.size();
        long end = (appIndex + 1) * shardCount / appIds.size() - 1;

        return LongStream.rangeClosed(start, end).boxed().toList();
    }

    private static int findAppIndex(String appId, List<String> appIds) {
        for (int i = 0; i < appIds.size(); i++) {
            if (appIds.get(i).equals(appId)) {
                return i;
            }
        }
        return -1;
    }
}

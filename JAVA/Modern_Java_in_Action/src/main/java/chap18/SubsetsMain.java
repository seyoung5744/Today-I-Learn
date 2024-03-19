package chap18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubsetsMain {

    public static void main(String[] args) {
        List<List<Integer>> subs = subsets(Arrays.asList(1, 4, 9));
        subs.forEach(System.out::println);
    }

    public static <T> List<List<T>> subsets(List<T> list) {
        if (list.isEmpty()) { // 입력 리스트가 비어있다면 빈 리스트 자신이 서브집합
            List<List<T>> ans = new ArrayList<>();
            ans.add(Collections.emptyList());
            return ans;
        }

        T first = list.get(0);
        List<T> rest = list.subList(1, list.size());

        // 빈 리스트가 아니면 먼저 하나의 요소를 꺼내고 나머지 요소의 모든 서브집합을 찾아서 subans로 전달한다. subans 는 절반의 정답을 포함한다.
        List<List<T>> subans = subsets(rest);

        // 정답의 나머지 절반을 포함하는 subans2는 subans의 모든 리스트에 처음 꺼낸 요소를 앞에 추가해서 만든다.
        List<List<T>> subans2 = insertAll(first, subans);

        // subans, subans2 를 연결하면 정답이 완성된다.
        return concat(subans, subans2);
    }

    public static <T> List<List<T>> insertAll(T first, List<List<T>> lists) {
        List<List<T>> result = new ArrayList<>();
        for (List<T> l : lists) {
            // 리스트를 복사한 다음에 복사한 리스트에 요소를 추가한다.
            // 구조체가 가변이더라도 저수준 구조를 복사하진 않는다. (Integer는 가변이 아니다)
            List<T> copyList = new ArrayList<>();
            copyList.add(first);
            copyList.addAll(l);
            result.add(copyList);
        }
        return result;
    }

    public static <T> List<List<T>> concat(List<List<T>> a, List<List<T>> b) {
        List<List<T>> r = new ArrayList<>(a);
        r.addAll(b);
        return r;
    }
}

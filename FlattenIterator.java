// Time Complexity: Flattening O(N) each element is traversed once
//Iteroator operations next O(1) hasNext O(1)
// Overall time complexity O(n)

// Space Complexity:  Stores all integers in a nested list. Space complexity O(n)
// Recursive stack O(D) where D is the maximum depth
// so overall complexity O(N + D)


import java.util.*;

interface NestedInteger {
    // @return true if this NestedInteger holds a single integer, rather than a nested list
    boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return null if this NestedInteger holds a single integer
    List<NestedInteger> getList();
}

class NestedIterator implements Iterator<Integer> {
    private final List<Integer> flatList;
    private int index;

    public NestedIterator(List<NestedInteger> nestedList) {
        flatList = new ArrayList<>();
        index = 0;
        flatten(nestedList);
    }

    private void flatten(List<NestedInteger> nestedList) {
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger()) {
                flatList.add(ni.getInteger());
            } else {
                flatten(ni.getList());
            }
        }
    }

    @Override
    public Integer next() {
        return flatList.get(index++);
    }

    @Override
    public boolean hasNext() {
        return index < flatList.size();
    }

    public static void main(String[] args) {
        // Example 1
        List<NestedInteger> nestedList1 = Arrays.asList(
                new NestedList(Arrays.asList(new NestedInt(1), new NestedInt(1))),
                new NestedInt(2),
                new NestedList(Arrays.asList(new NestedInt(1), new NestedInt(1)))
        );

        NestedIterator iterator1 = new NestedIterator(nestedList1);
        List<Integer> result1 = new ArrayList<>();
        while (iterator1.hasNext()) {
            result1.add(iterator1.next());
        }
        System.out.println(result1); // Output: [1, 1, 2, 1, 1]

        // Example 2
        List<NestedInteger> nestedList2 = Arrays.asList(
                new NestedInt(1),
                new NestedList(Arrays.asList(
                        new NestedInt(4),
                        new NestedList(Arrays.asList(new NestedInt(6)))
                ))
        );

        NestedIterator iterator2 = new NestedIterator(nestedList2);
        List<Integer> result2 = new ArrayList<>();
        while (iterator2.hasNext()) {
            result2.add(iterator2.next());
        }
        System.out.println(result2); // Output: [1, 4, 6]
    }
}

// Helper classes for testing
class NestedInt implements NestedInteger {
    private final Integer value;

    public NestedInt(int value) {
        this.value = value;
    }

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public Integer getInteger() {
        return value;
    }

    @Override
    public List<NestedInteger> getList() {
        return null;
    }
}

class NestedList implements NestedInteger {
    private final List<NestedInteger> list;

    public NestedList(List<NestedInteger> list) {
        this.list = list;
    }

    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public Integer getInteger() {
        return null;
    }

    @Override
    public List<NestedInteger> getList() {
        return list;
    }
}

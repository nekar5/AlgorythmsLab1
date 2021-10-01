package mainpkg.test;

import java.util.ArrayList;
import java.util.Stack;

/*по сути древо, которое имеет изначальную доску (root), с которой производятся действия,
которые сохраняются как новые состояния (a.k.a Nodes* : текущая доска + прошлое состояние + глубина)*/
class FindAnswer {
    /*состояние (узел/вершина дерева) используется в дальнейшем
    хранит текущее состояние доски и прошлое с его глубиной*/
    static class State {
        private Board currentBoard;
        private State previousState;
        private int depth;

        /*конструктор состояния (currentBoard - текущая доска, previousState - отцовское состояние)*/
        public State(Board currentBoard, State previousState) {
            this.currentBoard = currentBoard;
            this.previousState = previousState;
            if (this.previousState != null) {
                depth = this.previousState.depth + 1;
            } else {
                depth = 0;
            }
        }

        /*вывод доски*/
        public String toString() {
            return currentBoard.toString();
        }

        //получить глубину
        public int getDepth() {
            return depth;
        }
    }

    private Stack<State> stack = new Stack<State>();
    private State current;

    /*инициализация своеродного односвязного списка внутри стэка (ряды доски)*/
    public void start(Board root) {
        State s = new State(root, null);
        stack.add(s);
        /*заполняем копиями изначальной доски (8х)*/
        for (int i = 0; i < 7; i++) {
            s = new State(s.currentBoard, stack.get(i));
            stack.add(s);
        }
    }

    //BFS
    public Board performBFS(Board root) {
        /*информация*/
        //long timerStart = System.currentTimeMillis();
        long timerStart=System.nanoTime();
        int stateCount = 0;

        /*начало алгоритма*/
        start(root);

        /*текущее состояние*/
        current = stack.firstElement();

        /*доска, с которой работаем*/
        Board b = current.currentBoard;

        /*лист с рядами (их №), в которых либо нету ферзей
        либо не найдено нужное расположение ферзя*/
        ArrayList<Integer> emptyRows = new ArrayList<Integer>();

        while (true) {
            stateCount++;
            if (emptyRows.contains(current.depth) && current.depth < 7) {
                /*если ряд/колонна №depth подходящий для расстановки,
                но находится в ^листе, убираем с него*/
                if (b.rowIsSuitable(current.depth)) {
                    for (int i = 0; i < emptyRows.size(); i++)
                        if (emptyRows.get(i) == current.depth)
                            emptyRows.remove(i);

                    current = stack.get(current.depth + 1);
                } else {
                    while (emptyRows.size() > 0) {
                        /*заполняем пустые колонны рядов и убираем из листа*/
                        b.setQueen(emptyRows.get(0), b.getEmptyColumn());
                        emptyRows.remove(0);
                    }
                    /*меняем колонны и ряди пока не получим ответ*/
                    for (int col1 = 0; col1 < 7; col1++) {
                        stateCount++;
                        for (int col2 = col1 + 1; col2 < 8; col2++) {
                            stateCount++;
                            for (int row1 = 0; row1 < 7; row1++) {
                                stateCount++;
                                for (int row2 = row1 + 1; row2 < 8; row2++) {
                                    stateCount++;
                                    b.switchRows(row1, row2);
                                    if (b.checkBoard()) {
                                        /*выход*/
                                        showInfoBfs(timerStart, current.getDepth(), stack.size(), stateCount);
                                        return b;
                                    }
                                }
                            }
                            b.switchColumns(col1, col2);
                            if (b.checkBoard()) {
                                /*выход*/
                                showInfoBfs(timerStart, current.getDepth(), stack.size(), stateCount);
                                return b;
                            }
                        }
                    }
                }
            } else {
                /*если ряд НЕ готов и НЕ подходящий (для установки ферзя)*/
                if (!b.rowIsOk(current.depth) && !b.rowIsSuitable(current.depth))
                    //добавляем в лист
                    emptyRows.add(current.depth);
                /*если закончились ряды для проверки */
                if (emptyRows.size() == 0 && current.depth == 7 && b.checkBoard()) {
                    /*выход*/
                    showInfoBfs(timerStart, current.getDepth(), stack.size(), stateCount);
                    return b;
                }
                /*прошлись по рядам, нету ответа но еще есть ряды с несоответствиями*/
                else if (current.depth == 7 && emptyRows.size() != 0) {
                    /*начинаем  сизначального*/
                    current = stack.get(0);
                    /*в инных случаях - заново, но со следующим рядом*/
                }
                else {
                    stateCount++;
                    current = stack.get(current.depth + 1);
                }
            }
            /*выход если ищет больше 10 секунд*/
            if (System.nanoTime() - timerStart > Long.parseLong("10000000000")) {
                //throw new StackOverflowError("!!!OOPS SOMETHING WENT WRONG!!!");
                showInfoBfsError(timerStart, stack.size(), stateCount);
                return null;
            }
        }

    }

    /*стэк со всеми состояниями*/
    public Stack<State> getStack() {
        return stack;
    }

    /*BFS info*/
    public void showInfoBfs(long timerStart, int resultDepth, int statesInMemoryCount, int stateCount) {
        //long timerEnd = System.currentTimeMillis();
        long timerEnd = System.nanoTime();
        long total = (timerEnd - timerStart);
        System.out.println("-----Info-----\n" +
                "Time: " + total + " nanosec\n" +
                //"Result depth: " + resultDepth + "\n" +
                "States: " + stateCount + "\n" +
                "States in memory: " + statesInMemoryCount + "\n");
    }
    public void showInfoBfsError(long timerStart, int statesInMemoryCount, int stateCount){
        //long timerEnd = System.currentTimeMillis();
        long timerEnd = System.nanoTime();
        long total = (timerEnd - timerStart);
        System.out.println("-----Info-----\n" +
                "Time: " + total + " nanosec\n" +
                //"Result depth: " + resultDepth + "\n" +
                "States: " + stateCount + "\n" +
                "States in memory: " + statesInMemoryCount + "\n");
        System.out.println("Status: UNSOLVED");
    }
}

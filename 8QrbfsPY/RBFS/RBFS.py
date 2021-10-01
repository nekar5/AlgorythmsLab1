from RBFS.answer import Answer
from queue import PriorityQueue
import copy


class RBFS:
#инициализация
    def __init__(self, starting_board):
        self.starting_board = starting_board
        self.answer = None
        self.__checked = []
        self.iterations = 0
        self.states = 0
        self.ends = 0
        self.__queue = PriorityQueue()

#запустить рекурсию
    def start_rbfs(self):
        self.answer = self.__rbfs(self.starting_board, float("inf"))

#рекурсия
    def __rbfs(self, current, f_limit):
        self.iterations += 1
        if RBFS.__check_board(current):
            return Answer(True, result=current)
        successors = self.__get_successors(current)
        if len(successors) == 0:
            self.ends += 1
            return Answer(False, f_limit=float("inf"))
        for successor in successors:
            self.__queue.put((RBFS.__func(successor), successor))
        best = self.__queue.get()
        if best[0] > f_limit:
            return Answer(False, f_limit=best[0])
        alternative = self.__queue.get()
        self.__queue.empty()
        result = self.__rbfs(best[1], min(best, alternative)[0])
        if result is not None and result.is_solved:
            return result
        else:
            self.ends += 1
            return self.__rbfs(self.__queue.get()[1], float('inf'))

#получить дочерние состояния
    def __get_successors(self, board):
        result = []
        for i in range(8):
            for j in range(8):
                temp = copy.deepcopy(board)
                temp[i][0] = j
                if not board[i][0] == j and temp not in self.__checked:
                    result.append(copy.deepcopy(temp))
                    self.__checked.append(temp)
        self.states += len(result)
        return result

#эвристическая функция
    @staticmethod
    def __func(board):
        result = 0
        for i in range(8):
            for j in range(i + 1, 8):
                if board[i][0] == board[j][0] or board[i][1] == board[j][1] \
                        or abs(board[i][0] - board[j][0]) == abs(board[i][1] - board[j][1]):
                    result += 1
        return result

#проверка всей доски
    @staticmethod
    def __check_board(board):
        for i in range(8):
            for j in range(i + 1, 8):
                if board[i][0] == board[j][0] or board[i][1] == board[j][1] \
                        or abs(board[i][0] - board[j][0]) == abs(board[i][1] - board[j][1]):
                    return False
        return True
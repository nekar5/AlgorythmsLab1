from RBFS.RBFS import RBFS
import random
import time

#рандомные доски n шт
def generate_boards(n):
    result = []
    for _ in range(n):
        board = []
        for j in range(8):
            is_added = False
            while not is_added:
                queen = [random.randint(0, 7), j]
                if queen not in board:
                    board.append(queen)
                    is_added = True
        result.append(board)
    return result


#тест
test = [
    [[2, 0], [4, 1], [6, 2], [1, 3], [5, 4], [7, 5], [0, 6], [3, 7]],
    [[0, 0], [0, 1], [0, 2], [0, 3], [0, 4], [0, 5], [0, 6], [0, 7]],
    [[1, 0], [0, 1], [7, 2], [5, 3], [2, 4], [6, 5], [1, 6], [3, 7]]
]

#вывод
def printBoard(board):
    emptyboard = [[0, 0, 0, 0, 0, 0, 0, 0,],
                  [0, 0, 0, 0, 0, 0, 0, 0,],
                  [0, 0, 0, 0, 0, 0, 0, 0,],
                  [0, 0, 0, 0, 0, 0, 0, 0,],
                  [0, 0, 0, 0, 0, 0, 0, 0,],
                  [0, 0, 0, 0, 0, 0, 0, 0,],
                  [0, 0, 0, 0, 0, 0, 0, 0,],
                  [0, 0, 0, 0, 0, 0, 0, 0,]]
    for coords in board:
        emptyboard[coords[0]][coords[1]]=1
    print('\n'.join([''.join(['{:4}'.format(item) for item in row])
                     for row in emptyboard]))

#начало
for board in generate_boards(17): #n рандомных
#for board in test: #n заданных
    start_time = time.time()
    rbfs = RBFS(board)
    print('\nInput chessboard: ' + str(board))
    printBoard(board)

    rbfs.start_rbfs()
    print(rbfs.answer)

    print('States: ' + str(rbfs.states))
    print('Iterations: ' + str(rbfs.iterations))
    print('Deadends: ' + str(rbfs.ends))
    print('Time: '+"%s seconds" % (time.time() - start_time))
    print('------------------------------------------------------------')

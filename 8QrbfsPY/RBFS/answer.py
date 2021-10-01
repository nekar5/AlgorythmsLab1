class Answer:
#инициализация
    def __init__(self, is_solved, f_limit=None, result=None):
        self.is_solved = is_solved
        self.f_limit = f_limit
        self.result = result

#toString
    def __str__(self):
        emptyboard = [[0, 0, 0, 0, 0, 0, 0, 0, ],
                      [0, 0, 0, 0, 0, 0, 0, 0, ],
                      [0, 0, 0, 0, 0, 0, 0, 0, ],
                      [0, 0, 0, 0, 0, 0, 0, 0, ],
                      [0, 0, 0, 0, 0, 0, 0, 0, ],
                      [0, 0, 0, 0, 0, 0, 0, 0, ],
                      [0, 0, 0, 0, 0, 0, 0, 0, ],
                      [0, 0, 0, 0, 0, 0, 0, 0, ]]
        for coords in self.result:
            emptyboard[coords[0]][coords[1]] = 1
        print('\nResult chessboard: ' + str(self.result))
        print('\n'.join([''.join(['{:4}'.format(item) for item in row])
                         for row in emptyboard]))
        return ('Solved: ' + str(self.is_solved) + '\n')
from os import environ
def solve():
    with open('input.txt') as f:
        input = f.read().splitlines()
        all_defined = False
        iterations = 0
        while all_defined == False:
            iterations += 1
            for line in input:
                split = line.split(': ')
                try :
                    globals()[split[0]] = eval(split[1])
                    try :
                        root
                        all_defined = True
                    except:
                        NameError
                except: 
                    NameError
        return root
if __name__ == '__main__':
    print("Python")
    if environ.get('part') == 'part1':
        print(solve())
    else:
        print(solve())


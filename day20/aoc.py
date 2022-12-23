from os import environ

class Number:
    def __init__(self, value):
        self.value = value


def solve(key, iterations):
    original = []
    with open('input.txt') as f:
        input = f.read().splitlines()
        for line in input:
            original.append(Number(int(line)*key))

    clone = original.copy()
    for j in range(iterations):
        for i in range(len(clone)):
            index = original.index(clone[i])
            new_pos = (index + clone[i].value) % (len(clone)-1)
            original.insert(new_pos, original.pop(index))

    index_0 = original.index(list(filter(lambda x: x.value == 0, original))[0])
    grove_coordinates = [original[(1000 + index_0) % len(original)].value, original[(2000 + index_0) % len(original)].value, original[(3000 + index_0) % len(original)].value]
    return sum(grove_coordinates)

if __name__ == '__main__':
    print("Python")
    if environ.get('part') == 'part1':
        print(solve(1,1))
    else:
        print(solve(811589153, 10))

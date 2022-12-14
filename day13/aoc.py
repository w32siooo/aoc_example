from os import environ, getenv
import json


class Solution:
    def __init__(self):
        with open('input.txt') as f:
            input = f.read().splitlines()
        self.input = input

    def part1(self):
        cohort_index = 1
        match_index = 0
        list = []

        for line_nr in range(0, len(self.input), 3):  # 3 lines per cohort
            #  for line_nr in range(0,  len(self.input),3): # 3 lines per cohort
            if self.input[line_nr] != '':
                first_cohort = json.loads(self.input[line_nr])
                second_cohort = json.loads(self.input[line_nr+1])
                list.append(first_cohort)
                list.append(second_cohort)
                if compare_values(first_cohort, second_cohort):
                    match_index += cohort_index
                cohort_index += 1
        # sort the list using the compare_values function
        print("Number of matches: " + str(match_index))

    def part2(self):
        cohort_index = 1
        match_index = 0
        list = []

        for line_nr in range(0, len(self.input), 3):  # 3 lines per cohort
            #  for line_nr in range(0,  len(self.input),3): # 3 lines per cohort
            if self.input[line_nr] != '':
                first_cohort = json.loads(self.input[line_nr])
                second_cohort = json.loads(self.input[line_nr+1])
                list.append(first_cohort)
                list.append(second_cohort)
                if compare_values(first_cohort, second_cohort):
                    match_index += cohort_index
                cohort_index += 1
        # sort the list using the compare_values function
        list.append([6])
        list.append([2])
        sorted_list = quick_sort(list)
        sorted_list.reverse()
        answer = (sorted_list.index([6])+1)*(sorted_list.index([2])+1)
        print("cipher:",answer)


def quick_sort(list):
    if len(list) <= 1:
        return list
    else:
        pivot = list.pop()
    items_greater = []
    items_lower = []
    for item in list:
        if compare_values(item, pivot):
            items_greater.append(item)
        else:
            items_lower.append(item)
    return quick_sort(items_lower) + [pivot] + quick_sort(items_greater)


def compare_values(left, right):
    try:
        for i in range(len(left)):
            # Loop through the lists and compare the values
            if isinstance(left[i], int) and isinstance(right[i], int):
                if left[i] < right[i]:
                    return True
                elif left[i] > right[i]:
                    return False
                else:
                    continue
            # if one is a list and the other is not, compare the list to the value and convert the value to a list
            elif isinstance(left[i], list) and isinstance(right[i], int):
                return compare_values(left[i], [right[i]])
            elif isinstance(left[i], int) and isinstance(right[i], list):
                return compare_values([left[i]], right[i])
            else:
                if compare_values(left[i], right[i]) is None:
                    continue
                else:
                    return compare_values(left[i], right[i])
        if len(left) < len(right):
            return True
    except:
        return False

if __name__ == '__main__':
    print("Python")
    if environ.get('part') == 'part2':
        sol = Solution().part2()
    else:
        sol = Solution().part1()

from os import environ, getenv
import re
import numpy as np

class Solution:
    def __init__(self):
        self.sensors = []
        self.beacons = []
        self.scanned_coordinates = set()
        self.to_scan = 2000000
        self.parse_input()
        self.scan_until_all_beacons_scanned()

    def part1(self):

        print(len(self.scanned_coordinates))

    def parse_input(self):
        numbers_extracted = []
        self.to_scan = 2000000
        with open("input.txt", "r") as f:
            for line in f:
                numbers_extracted += [int(x) for x in re.findall(r"[-0-9]+", line)]
        for i in range(0,len(numbers_extracted)-2,4):
            beacon = [numbers_extracted[i+2],numbers_extracted[i+3]]
            sensor = [numbers_extracted[i],numbers_extracted[i+1]]
            dist = calculate_manhattan_distance(sensor,beacon)
            self.beacons.append((beacon[0],beacon[1]))
            self.sensors.append([sensor[0],sensor[1],dist])

    def scan_until_all_beacons_scanned(self):
        for sensor in self.sensors:
            points = solve_first(sensor[0],sensor[1],sensor[2],self.to_scan)
            for point in points:
                self.scanned_coordinates.add(point)
                
def solve_first(sensor_x,sensor_y,sensor_range,to_scan):
    distance_from_target = abs(to_scan - sensor_y)
    #print("sensor is range from target by:", abs(sensor_range - distance_from_target))
    if sensor_range - distance_from_target > 0: # if sensor can reach target 
        min_range_from_to_scan = sensor_x + distance_from_target - sensor_range
        max_range_from_to_scan = sensor_x - distance_from_target + sensor_range
        range = np.arange(min_range_from_to_scan, max_range_from_to_scan,1)
        return range # return list of x coordinates 
    else: # if sensor can't reach target
        return [] # return empty list


def calculate_manhattan_distance(sensor, beacon):
    return abs(sensor[0]-beacon[0])+abs(sensor[1]-beacon[1])


if environ.get('part') == 'part2':
    sol = Solution().part2()
else:
    sol = Solution = Solution().part1()

            

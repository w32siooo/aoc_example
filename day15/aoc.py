import re
import numpy as np
from itertools import product

class Solution:
    def __init__(self):
        self.sensors = []
        self.beacons = []
        self.scanned_coordinates = set()
#        self.part1 = self.solve_part1()
        self.parse_input()

        print(self.sensors)
        #
      #  self.init_beacons()
        #self.part2 = self.solve_part2()
        self.scan_until_all_beacons_scanned()
        self.if_in_row_10()

    def if_in_row_10(self):
        count = 0

        print(len(self.scanned_coordinates))
        print(self.scanned_coordinates)
        print(count)
       

    def parse_input(self):
        numbers_extracted = []

        with open("test.txt", "r") as f:
            for line in f:
                ##add all matches for regex [-0-9]+ to input
                numbers_extracted += [int(x) for x in re.findall(r"[-0-9]+", line)]
        for i in range(0,len(numbers_extracted)-2,4):
            beacon = [numbers_extracted[i+2],numbers_extracted[i+3]]
            sensor = [numbers_extracted[i],numbers_extracted[i+1]]
            dist = calculate_manhattan_distance(sensor,beacon)
            self.beacons.append((beacon[0],beacon[1]))
            self.sensors.append([sensor[0],sensor[1],dist])

    def scan_until_all_beacons_scanned(self):

        for sensor in self.sensors:
            sensor_index = self.sensors.index(sensor)
            sensor_range = sensor[2]
            scan_count = 0
            scan_index = 0
            # range from -2000000 to 2000000
            range = np.arange(-2,2000,1)
            print(range)
            true = True
            while isInside(sensor[0],sensor[1],scan_index,self.beacons[sensor_index][0],self.beacons[sensor_index][1]) == False:
              #  print("sensor: ",sensor," scanned: ",scan_count," coordinates")
                scan_index += 1
                
                points = returnPointsInsideList(sensor[0],sensor[1],scan_index,range,10)
                for point in points:
                    if point not in self.beacons:
                        self.scanned_coordinates.add(point)
                        scan_count += 1
            print("sensor: ",sensor," at ",sensor_index," scanned: ",scan_count," coordinates")

    def init_beacons(self):
        for beacon in self.beacons:
            if beacon not in self.scanned_coordinates:    
                self.scanned_coordinates.append(beacon)

def returnPointsInsideList(circle_x, circle_y, rad, xList, yConst):
     
    # Compare radius of circle
    # with distance of its center
    # from given point
    points = []
    for x in xList:
        if ((x - circle_x) * (x - circle_x) +
            (yConst - circle_y) * (yConst - circle_y) <= rad * rad):
            points.append((x,yConst))
    return points

def isInside(circle_x, circle_y, rad, x, y):
    # Compare radius of circle
    # with distance of its center
    # from given point
    if ((x - circle_x) * (x - circle_x) +
        (y - circle_y) * (y - circle_y) <= rad * rad):
        return True;
    else:
        return False;
 
# Driver Code
x = 1;
y = 1;
circle_x = 0;
circle_y = 1;
rad = 2;
if(isInside(circle_x, circle_y, rad, x, y)):
    print("Inside");
else:
    print("Outside");

    def solve_part1(self):
        return 0
        

    def solve_part2(self):
        return 0
def calculate_manhattan_distance(sensor, beacon):
    return abs(sensor[0]-beacon[0])+abs(sensor[1]-beacon[1])

def points_in_circle_midpoint(radius, x0=0, y0=0):
    x, y = radius, 0
    decision = 1 - x
    points = []
    while y <= x:
        if y0+y > 2100000:
            break
        else :
            if y0+y == 2000000:
                points.append((x + x0, y + y0))
                points.append((y + x0, x + y0))
                points.append((-x + x0, y + y0))
                points.append((-y + x0, x + y0))
                points.append((-x + x0, -y + y0))
                points.append((-y + x0, -x + y0))
                points.append((x + x0, -y + y0))
                points.append((y + x0, -x + y0))
            y += 1
            if decision <= 0:
                decision += 2 * y + 1
            else:
                x -= 1
                decision += 2 * (y - x) + 1
    return points


def points_in_circle_np(radius, x0=0, y0=0):
    x_ = np.arange(x0 - radius - 1, x0 + radius + 1, dtype=int)
    y_ = np.arange(y0 - radius - 1, y0 + radius + 1, dtype=int)
    x, y = np.where((x_[:,np.newaxis] - x0)**2 + (y_ - y0)**2 <= radius**2)
    # x, y = np.where((np.hypot((x_-x0)[:,np.newaxis], y_-y0)<= radius)) # alternative implementation
    for x, y in zip(x_[x], y_[y]):
        yield x, y    
    
Solution = Solution()
            

def points_in_circle(radius,x0,y0):
    x_ = np.arange(x0 - radius - 1, x0 + radius + 1, dtype=int)
    y_ = np.arange(y0 - radius - 1, y0 + radius + 1, dtype=int)
    for x, y in product(range(int(radius) + 1), repeat=2):
        if x**2 + y**2 <= radius**2:
            yield from set(((x, y), (x, -y), (-x, y), (-x, -y),))
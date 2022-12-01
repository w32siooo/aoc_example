package com.andreas
import java.io.*
println "groovy"

File file = new File("input.txt")

def list = [] // empty list
def count = 0
def largest = 0
file.eachLine { line ->
   if(line==""){
    count++
   } else {
        if(list[count] == null){
            list.add(line.toInteger())
        }else{
          list[count] = list[count]+line.toInteger()
         }
        if(list[count] > largest){
             largest = list[count]
         }
   }
}


if(System.getenv("part").equals("part1")){
    print largest
}
if(System.getenv("part").equals("part2")){
    list.sort()
    def top3 = list[-3]+list[-2]+list[-1]
    print top3
}
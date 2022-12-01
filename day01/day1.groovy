import java.io.*
println "groovy"
File file = new File("input.txt")

def list = [] // empty list
def count = 0
file.eachLine { line ->
   if(line==""){
    count++
   }else {
    if(list[count]==null){
            list.add(line.toInteger())
    }else{
        list[count] = list[count]+line.toInteger()
    }
   }
        
}
list = list.sort()
if(System.getenv("part").equals("part1")){
    print list[count]
}
if(System.getenv("part").equals("part2")){
    print "${list[count-2]+list[count-1]+list[count]}"
}

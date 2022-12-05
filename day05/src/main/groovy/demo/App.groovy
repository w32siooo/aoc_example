
package demo

import groovy.transform.CompileStatic

import java.nio.file.Files
@CompileStatic
class App {
    static Map<Integer,Stack<Character>> extractCrates(){
        def msp = [:] as Map<? extends Integer, ? extends LinkedList<Character>> as TreeMap<Integer, LinkedList<Character>>
        Files.lines(new File("input.txt").toPath()).forEach(line -> {
            def counter = 0
            line.toCharArray().toList().each { c ->
                counter++
                if (Character.isLetter(c) && Character.isUpperCase(c)) {
                    def se = msp.get(counter)
                    if (se == null) se = [] as LinkedList<Character>
                    se.push(c)
                    msp.put(counter, se)
                }
            }
        }
        )
        def crates = [:] as Map<Integer, Stack<Character>>
        def crateCounter = 1
        msp.each { key, value ->
            {
                Stack<Character> stack = new Stack<>()
                value.forEach() { s ->
                    stack.push(s)
                }
                crates.put(crateCounter, stack)
                crateCounter++
            }
        }
        return crates
    }

    static String getDay5Part2Solution(){
        def crates =  extractCrates()

        Files.lines(new File("input.txt").toPath())
                .filter(line -> line.startsWith("move"))
                .map(line -> line.findAll(~/\d+/))
                .map(listOfNumbers -> listOfNumbers.stream()
                        .map(strN -> Integer.parseInt(strN))
                        .toList())
                .forEach(instruction -> {
                    def toAdd = [] as ArrayList<Character>
                    for (int i = 0; i < instruction[0]; i++) {
                        def stackOfCrates = crates.get(instruction[1])
                        def item = stackOfCrates.pop()
                        toAdd.push(item)
                        crates.get(instruction[2])
                    }
                    crates.get(instruction[2]).addAll(toAdd)
                })

        def res = ""
        crates.entrySet().forEach({ entry ->
            res += entry.value.peek()
        })
        return res
    }

    static String getDay5Solution() {
        def crates = extractCrates()
        Files.lines(new File("input.txt").toPath())
                .filter(line -> line.startsWith("move"))
                .map(line -> line.findAll(/\d+/))
                .map(listOfNumbers -> listOfNumbers.stream()
                        .map(strN -> Integer.parseInt(strN))
                        .toList())
                .forEach(instructions -> {
                    for (int i = 0; i < instructions.get(0); i++) {
                        def stackOfCrates = crates.get(instructions.get(1))
                        def item = stackOfCrates.pop()
                        crates.get(instructions.get(2)).push(item)
                    }
                })

        def resp = ""
        crates.entrySet().forEach({ entry ->
            resp += entry.value.peek()
        })
        return resp
    }

    static void main(String[] args) {
        println "Groovy"

        if (System.getenv("part") == "part1") {
            println getDay5Solution()
        } else {
            println getDay5Part2Solution()
        }
    }
}



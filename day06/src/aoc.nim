import std/[strutils, os]
import sets

let input = readFile("input.txt")
            .strip()
                
proc solve(cutoff: int): int =
    var counter = 0
    for char in input:
        counter += 1
        var buffer = toHashSet(input[counter .. counter+cutoff-1])
        if buffer.len == cutoff:
            return counter + cutoff
            break

when isMainModule:
  echo "Nim"
  echo case getEnv("part"):
      of "part2": intToStr(solve(14))
      of "part1": intToStr(solve(4))
      else: "Unknown part"

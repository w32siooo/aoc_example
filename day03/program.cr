puts "Crystal"
ENV["part"] == "part1" ? part1 : part2

def part1
  content = File.read("input.txt")
  res = 0 
  content.each_line do |line|
    first_half = line.byte_slice(0, line.size//2).split(//).to_set
    second_half = line.byte_slice(line.size//2, line.size).split(//).to_set
    combined = first_half & second_half
    res += combined.first[0].ord > 90 ? combined.first[0].ord-97+1  : combined.first[0].ord-65+27
  end
  puts res
end

def part2
  content = File.read("input.txt")

  set1 = Set(String).new
  set2 = Set(String).new
  set3 = Set(String).new
  counter = 0

  res2 = 0 
  content.each_line do |line|
    line_set = line.split(//).to_set
    if counter % 3 == 0
      set1 = line_set
    elsif counter % 3 == 1
      set2 = line_set
    else
      set3 = line_set
    end

    if counter % 3 == 2
      combined = set1 & set2 & set3
      res2 += combined.first[0].ord > 90 ? combined.first[0].ord-97+1  : combined.first[0].ord-65+27
    end
    counter = counter + 1    
  end

  puts res2
end
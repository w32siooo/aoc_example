def part1()
    content = File.read("input.txt")
    list = [] of Int32
    count = 0
    content.each_line do |line|
        if line == ""
            list << count
            count = 0
        else
            count = count + line.to_i
        end   
    end
    return list
end

puts "Crystal"
ENV["part"] == "part1" ? puts part1().max : puts part1().sort.reverse.first(3).sum
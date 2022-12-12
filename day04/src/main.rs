use std::env;
use std::fs;
use std::path::Path;
use std::collections::HashSet;

fn main() {
    let part1 = |s: &Vec<Vec<i32>>| -> bool {
        (s[0][0] <= s[1][0] && s[0][1] >= s[1][1]) ||
            (s[0][0] >= s[1][0] && s[0][1] <= s[1][1])
    };

    let part2 = |s: &Vec<Vec<i32>>| -> bool {
        (s[0][1] >= s[1][0] && s[0][1] <= s[1][1]) ||
            (s[1][1] >= s[0][0] && s[1][1] <= s[0][1]) ||
            (s[0][0] >= s[1][0] && s[0][0] <= s[1][1]) ||
            (s[1][0] >= s[0][0] && s[1][0] <= s[0][1])
    };

    let input_path = Path::new("input.txt");
    let input = fs::read_to_string(input_path)
        .expect("Unable to read file");

    let lines = input.lines();

    let env_var = env::var("part").unwrap();
    let sol = if env_var == "part1" {
        part1
    } else {
        part2
    };

    let mut result = 0;
    for line in lines {
        let split: Vec<&str> = line.split(",").collect();
        let split_values: Vec<Vec<i32>> = split
            .iter()
            .map(|x| {
                x.split("-")
                    .map(|s| s.parse().unwrap())
                    .collect()
            })
            .collect();

        if sol(&split_values) {
            result += 1;
        }
    }
    println!("Rust");

    println!("{}", result);
}

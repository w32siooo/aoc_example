use std::env;
use std::fs::File;
use std::io::BufReader;
use std::io::BufRead;


fn read_input_as_chars(path: &str) -> i32 {
    let part = match env::var("part") {
        Ok(val) => val,
        Err(_e) => "part1".to_string(),
    };


    let reader = BufReader::new(File::open(path).expect("Cannot open file.txt"));
    let mut score = 0;
    for line in reader.lines() {
    for word in line {
        let char_vec: Vec<char> = word.chars().collect();
        let elf_move = char_vec[0];
        let mut player_move = char_vec[2];
        if part == "part2" {
            if elf_move == 'A' && player_move == 'X' {player_move = 'Z'}
            else if elf_move == 'A' && player_move == 'Y' {player_move = 'X';}
            else if elf_move == 'A' && player_move == 'Z' {player_move = 'Y';}
    
            else if elf_move == 'B' && player_move == 'X' {player_move = 'X';}
            else if elf_move == 'B' && player_move == 'Y' {player_move = 'Y';}
            else if elf_move == 'B' && player_move == 'Z' {player_move = 'Z';}
    
            else if elf_move == 'C' && player_move == 'X' {player_move = 'Y';}
            else if elf_move == 'C' && player_move == 'Y' {player_move = 'Z';}
            else if elf_move == 'C' && player_move == 'Z' {player_move = 'X';}
        } 

        if player_move == 'X'{ score += 1; }    
        if player_move == 'Y'{ score += 2; }   
        if player_move == 'Z' {score += 3;  } 
        
        if elf_move == 'A' && player_move == 'Y' || elf_move == 'B' && player_move == 'Z' || elf_move == 'C' && player_move == 'X' 
        { score += 6; } //win  
        else if elf_move == 'A' && player_move == 'Z' || elf_move == 'B' && player_move == 'X' || elf_move == 'C' && player_move == 'Y'
     {
        score += 0;     //LOSE
    } else {
        score += 3;    //DRAW
    }
    }
}
return score;

}

fn main() {
    println!("Rust");
    let res = read_input_as_chars("input.txt");
    println!("{}", res);

}
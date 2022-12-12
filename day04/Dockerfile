FROM rust:1.61 as builder
WORKDIR /usr/src/day04
COPY . .
RUN cargo install --path .

FROM debian:buster-slim
COPY --from=builder /usr/local/cargo/bin/day04 /usr/local/bin/day04
COPY input.txt .
CMD ["day04"]
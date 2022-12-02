FROM rust:1.61 as builder
WORKDIR /usr/src/day02
COPY . .
RUN cargo install --path .

FROM debian:buster-slim
COPY --from=builder /usr/local/cargo/bin/day02 /usr/local/bin/day02
COPY input.txt .
CMD ["day02"]
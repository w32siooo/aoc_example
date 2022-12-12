FROM crystallang/crystal:0.35.1-alpine
COPY . .
RUN crystal build aoc.cr --static
CMD ["./program"]
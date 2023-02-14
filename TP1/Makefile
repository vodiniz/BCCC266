all: main.o cpu.o generator.o
	@gcc main.o cpu.o generator.o -o exe -g
	rm -r *.o
main.o: main.c
	@gcc main.c -c -Wall -g
cpu.o: cpu.c
	@gcc cpu.c -c -Wall -g
generator.o: generator.c
	@gcc generator.c -c -Wall -g
run:
	@./exe

all: main.o cpu.o generator.o instruction.o memory.o mmu.o
	@gcc main.o cpu.o generator.o instruction.o memory.o mmu.o -o exe  -lm -g
	@rm -r *.o
main.o: main.c
	@gcc main.c -c -Wall -g
cpu.o: cpu.c
	@gcc cpu.c -c -Wall -g
generator.o: generator.c
	@gcc generator.c -c -Wall -g
instruction.o: instruction.c
	@gcc instruction.c -c -Wall -g
memory.o: memory.c
	@gcc memory.c -c -Wall -g
mmu.o: mmu.c
	@gcc mmu.c -c -Wall -g
run:
	@./exe

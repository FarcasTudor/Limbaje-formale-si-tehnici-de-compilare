bits 32
global start

extern exit
import exit msvcrt.dll

extern scanf
import scanf msvcrt.dll

extern printf
import printf msvcrt.dll

segment data use32 class=data
	 a times 4 db 0
	 format db "%d", 0

segment code use32 class=code
	start:
		push dword a
		push dword format
		call [scanf]
		add ESP, 4 * 2

		mov AL, [a]
		add AL, 20
		sub AL, 10
		mov [a], AL

		mov AL, [a]
		mov DL, 10
		mul DL
		mov [a], AL

		mov AL, [a]
		mov DL, 5
		div DL
		mov [a], AL

		push dword [a]
		push dword format
		call [printf]
		add ESP, 4 * 2

		push dword 0
		call [exit]

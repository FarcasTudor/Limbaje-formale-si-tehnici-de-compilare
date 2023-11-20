#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include "IdentificareAtomiLexicali.h"
#include <vector>
#include <regex>
#include "Entity.cpp"

using namespace std;

class SymbolTable {
public:
	int currentCod;
	map<string, int> table;

public:
	SymbolTable() {
		currentCod = 1000;
	}

	int getOrAdd(const string& symbol) {
		if (table.find(symbol) != table.end()) {
			return table[symbol];
		}
		else {
			table[symbol] = currentCod++;
			return table[symbol];
		}
	}
};

map<string, int> table = {
	{"ID",0},
	{"CONST", 1},
	{"int",2},
	{"main",3},
	{"(", 4},
	{")", 5},
	{"{", 6},
	{"}", 7},
	{",", 8},
	{";", 9},
	{":", 10},
	{".", 11},
	{"/", 12},
	{"=", 13},
	{"+", 14},
	{"-", 15},
	{"*", 16},
	{"<", 17},
	{">", 18},
	{"if", 19},
	{"else", 20},
	{"while", 21},
	{"return", 22},
	{"for", 23},
	{"char", 24},
	{"float", 25},
	{"bool", 26},
	{"true", 27},
	{"false", 28},
	{"&&", 29},
	{"||", 30},
	{"<<", 31},
	{">>", 32},
	{"++", 33},
	{"--", 34},
	{"%", 35},
	{"&", 36},
	{"|", 37},
	{"^", 38},
	{"~", 39},
	{"?", 40},
	{"std::cin", 41},
	{"std::cout", 42}
};

bool isSeparator(char ch) {
	return ch == ';' || ch == ',' || ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == '=' || ch == ' ' || ch == '\t';
}

void SeparareAtomi(std::ifstream& fin, std::ofstream& fout)
{
	string currentWord;
	char ch;
	while (fin.get(ch)) {
		if (isSeparator(ch)) {
			if (!currentWord.empty()) {
				fout << currentWord << '\n';
				currentWord.clear();
			}
			if (ch == '(' || ch == ')' || ch == ',' || ch == '=' || ch == ';' || ch == '{' || ch == '}') {
				fout << ch << '\n';
			}
			else if (ch != ' ' && ch != '\t') {
				fout << ch;
			}
		}
		else {
			if (ch != '\n') {
				currentWord += ch;
			}
			else if (!currentWord.empty()) {
				fout << currentWord << '\n';
				currentWord.clear();
			}
		}
	}

	if (!currentWord.empty()) {
		fout << currentWord << '\n';
	}
}

bool isIdentifier(string str) {
	regex pattern("^[a-zA-Z][a-zA-Z0-9]*$");

	return regex_match(str, pattern);
}

bool isConstant(string str) {
	regex pattern("^[0-9]+$");

	return regex_match(str, pattern);
}

int main() {
	ifstream fin("input.in");
	ofstream fout("atoms.out");

	if (!fin.is_open() || !fout.is_open()) {
		cout << "Failed to open files" << '\n';
		return 1;
	}

	SeparareAtomi(fin, fout);

	fin.close();
	fout.close();

	ifstream fout_read("atoms.out");

	if (!fout_read.is_open()) {
		cout << "Failed to open the output file for reading" << '\n';
		return 1;
	}

	vector<Entity> fip;
	SymbolTable ts;
	int currentCod = 1000;
	bool uniqueValue = false;
	string line;
	while (getline(fout_read, line)) {
		uniqueValue = true;
		if (table.find(line) != table.end()) {
			fip.push_back(Entity(line, table[line]));
		}
		else {
			for (const auto& symbol : ts.table) {
				if (symbol.first == line) {
					fip.push_back(Entity(line, symbol.second));
					uniqueValue = false;
					break;
				}
			}
			if (uniqueValue) {
				if (isIdentifier(line)) {
					bool isLengthValid = true;
					if (line.length() > 250) {
						isLengthValid = false;
					}
					if (isLengthValid) {
						fip.push_back(Entity(line, 0));
						ts.getOrAdd(line);
					}
					else {
						fip.push_back(Entity("Eroare lexicala: " + line, -1));
						break;
					}
				}
				else {
					if (isConstant(line)) {
						fip.push_back(Entity(line, 1));
						ts.getOrAdd(line);
					}
					else {
						fip.push_back(Entity("Eroare lexicala: " + line, -1));
						break;
					}
				}
			}
		}
	}


	ofstream fout_fip("tabela_FIP.out");
	ofstream fout_ts("tabela_TS.out");

	if (!fout_fip.is_open() || !fout_ts.is_open()) {
		cout << "Failed to open the output files" << '\n';
		return 1;
	}
	for (Entity entity : fip) {
		entity.print(fout_fip);
	}

	for (const auto& symbol : ts.table) {
		fout_ts << "Simbol: " << symbol.first << ", Cod: " << symbol.second << '\n';
	}

	fout_read.close();
	return 0;
}

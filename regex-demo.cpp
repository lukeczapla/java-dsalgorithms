#include <stdio.h>
#include <string.h>
#include <regex.h>

regex_t regex;

void process_line(char *line, int n) {
	int len = strlen(line);
	regmatch_t match;
	int index = 0;
	bool first = true;
	do {
		//printf("starting at %d\n", index);
		int result = regexec(&regex, line+index, 1, &match, 0);
		if (result == REG_NOMATCH) {
			//printf("Skipping line %d\n", n);
			return;
		}
		//printf("%d\n", match.rm_eo);	
		//printf("%d %d\n", match.rm_so, match.rm_eo);
		if (first) {
			printf("Line %d: %s", n, line);
			first = false;
		}
		printf("Line %d, position %d: %.*s\n", n, index+match.rm_so, match.rm_eo-match.rm_so, line+index+match.rm_so);
		index += match.rm_eo;
	} while (match.rm_eo != 0);
}

int main(int argc, char **argv) {
	const char *ex = "[+(]{0,2}[0-9]{2,4}[ )-]{0,2}[0-9]{3,5}[ -]{0,2}[0-9]{3,5}";
	const char *input = "JoicE (201)256-7465 hello";
	regmatch_t match;
	//printf("%lu %lu\n", sizeof(regoff_t), sizeof(int));
	if (int ret = regcomp(&regex, ex, REG_EXTENDED) != 0) {
		printf("error...\n");
		char errormsg[256];
		regerror(ret, &regex, errormsg, sizeof(errormsg));
		printf("Error %s (ret = %d) for regex %s\n", errormsg, ret, ex);
	} else {
		printf("compiled, testing sample...\n");
		regexec(&regex, input, 1, &match, 0);
		printf("%d %d\n", match.rm_so, match.rm_eo);
		printf("%.*s\n", match.rm_eo - match.rm_so, input+match.rm_so);
	}

	printf("analyzing\n");
	
	FILE *f = fopen("input.txt", "r");
	size_t line_size = 0;
	ssize_t linelen = 0;
	char *line;

	int line_number = 1;
	while (linelen = getline(&line, &line_size, f) > 0) {
		//printf("%s", line);
		process_line(line, line_number);
		line_number++;
	}
	
	fclose(f);
	
}


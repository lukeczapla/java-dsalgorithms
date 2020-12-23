#include <stdio.h>
#include <cstdlib>
#include <string.h>
#include <regex.h>

#define TOTAL 20000
#define N 256

regex_t regex;

void process_line(char *line, int n, FILE *fout) {
	int len = strlen(line);
	regmatch_t match;
	int index = 0;
	bool first = true;
	do {
		int result = regexec(&regex, line+index, 1, &match, 0);
		if (result == REG_NOMATCH) {
			return;
		}
		if (first) {
			fprintf(fout, "Line %d: %s", n, line);
			first = false;
		}
		fprintf(fout, "Line %d, position %d: %.*s\n", n, index+match.rm_so, match.rm_eo-match.rm_so, line+index+match.rm_so);
		index += match.rm_eo;
	} while (match.rm_eo != 0);
}

int main(int argc, char **argv) {
	const char *ex = "[+]{0,2}[0-9]{0,4}[ (-]{0,2}[0-9]{3,5}[ )-]{0,2}[0-9]{3,5}[ -]{0,2}[0-9]{1,5}";
	regmatch_t match;
	//printf("%lu %lu\n", sizeof(regoff_t), sizeof(int));
	if (int ret = regcomp(&regex, ex, REG_EXTENDED) != 0) {
		printf("error...\n");
		char errmsg[256];
		regerror(ret, &regex, errmsg, sizeof(errmsg));
		printf("Error msg :%s (ret = %d) for regex:%s\n", errmsg, ret, "phone");
	} else {
		printf("compiled, running...\n");
	}

	printf("analyzing\n");
        FILE **f = new FILE*[TOTAL];
        FILE **fout = new FILE*[TOTAL];

	char *fname = (char *)malloc(64);
	bool done[N];
	char **line = new char*[N];
	int line_number[N];
	
	size_t line_size[N];
	ssize_t linelen[N];


    for (int batch = 0; batch <= TOTAL / N; batch++) {
	printf("Batch %d\n", batch);
	int n = N;
	if (batch == TOTAL / N) n = TOTAL % N + 1;
	if (batch != 0) for (int i = 0; i < n; i++) {
		fclose(f[i]);
		fclose(fout[i]);
	}
	printf("Batch %d\n", batch);

        for (int i = 0; i < n; i++) {
                sprintf(fname, "input%d.txt", batch*N+i);
                f[i] = fopen(fname, "r");
                sprintf(fname, "output%d.txt", batch*N+i);
                fout[i] = fopen(fname, "w");
                done[i] = false;
                line_number[i] = 1;
        }
        #pragma omp for
        for (int i = 0; i < n; i++) {
		while (linelen[i] = getline(&line[i], &line_size[i], f[i]) > 0) {
			process_line(line[i], line_number[i], fout[i]);
			line_number[i]++;
		}
	}
    }

    delete[] f, fout, line;
    free(fname);

}


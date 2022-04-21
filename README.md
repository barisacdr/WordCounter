# WordCounter
Word counter project on maven

For start make sure you copy the project to your computer from command line. Type these codes one by one:
git clone https://github.com/barisacdr/WordCounter.git
cd WordCounter
mvn clean package

Here's the functions list:

**For calculating number of tokens**: java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task NumOfTokens
**For calculating number of unique tokens**: java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task NumOfTokens -u

**For calculating number of most frequent 5 tokens**: java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task FrequentTerms
**For calculating number of most frequent 10 tokens**: java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task FrequentTerms -topN 10
**For calculating number of least frequent 10 tokens**: java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task FrequentTerms -topN 10 -r

**For calculating istatistics of texts**: java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task TermLengthStats
**For calculating istatistics unique items of texts**: java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task TermLengthStats -u

**For searching words starting by "*" (type which chars you want instead of * after -start)**: 
java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task TermsStartWith -start de 

**For searching words starting by "*" reversed order(type which chars you want instead of * after -start)**: 
java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task TermsStartWith -start de -r

**For searching 10 words starting by "*" reversed order(type which chars you want instead of * after -start)**:
java -jar target\WordCounter.jar sample1.txt sample2.txt sample3.txt -task TermsStartWith -start de -r -topN 10


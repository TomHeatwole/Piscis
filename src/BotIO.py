import time
import sys

class BotIO:

    def __init__(self, botNum):
        self.botNum = botNum
        self.bot = self.createBot()
        self.infile = 'MatchToBot' + str(botNum) + '.txt'
        self.outfile = 'Bot' + str(botNum) + 'ToMatch.txt'
        self.play = True    

    def start(self):
        with open(self.infile, 'r') as f:
            while self.play:
                where = f.tell()
                line = f.readline()
                if not line:
                    time.sleep(1)
                    f.seek(where)
                else:
                    self.processInput(line)

    def output(self, s):
        if not len(s):
            return
        s += '\n'
        with open(self.outfile, 'a') as f:
            f.write(s)

    def processInput(self, s):
        if not len(s):
            return
        if s[0] == 'X':
            self.play = False
            return
        self.output(self.getBotResponse(s))

    def getBotResponse(self,s):
        raise NotImplementedError("Please implement this method.")

    def createBot(self):
        raise NotImplementedError("Please implement this method.")


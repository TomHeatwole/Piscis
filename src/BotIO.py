import time

class BotIO:

    def __init__(self, botNum):
        self.botNum = botNum
        self.bot = self.createBot()
        self.infile = open("MatchToBot" + str(botNum) + ".txt",'r')
        self.outfile = open("Bot" + str(botNum) + "ToMatch.txt",'w')
        self.play = True    

    def start(self):
        while self.play:
           where = self.infile.tell()
           line = self.infile.readline()
           if not line:
               time.sleep(0.1)
               self.infile.seek(where)
           else:
               self.processInput(line)

    def output(self, s):
        if not len(s):
            return
        s += '\n'
        self.outfile.write(s)
        print('here')

    def processInput(self, s):
        if not len(s):
            return
        if s[0] == 'X':
            play = False
            return
        self.output(self.getBotResponse(s))

    def getBotResponse(self,s):
        raise NotImplementedError("Please implement this method.")

    def createBot(self):
        raise NotImplementedError("Please implement this method.")


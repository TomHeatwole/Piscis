from BotIO import BotIO

class HoldEmBotIO(BotIO):

    def __init__(self, botNum):
        BotIO.__init__(self, botNum) 

    def getBotResponse(self,s):
        if self.botNum == 1:
            return self.bot.processMatchInfo(s)
        return self.bot.processMatchInfo(s)

    def createBot(self):
        if self.botNum == 1:
            from Bot1 import Bot1
            return Bot1()
        from Bot2 import Bot2
        return Bot2()

class SampleBot:
    def __init__(self):
        self.dummyVar = 'dummyVar'
        #fill in later
    
    def processMatchInfo(self, info):
        if info.split(" ")[0] == 'action':
            return "check"
        return ""         

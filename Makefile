all: tests

tests: 
	lein midje
deploy:
	lein deploy clojars

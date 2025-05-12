.PHONY: all teardown setup

all: teardown setup

teardown:
	docker-compose -f stack.yml down -v

setup:
	docker-compose -f stack.yml up -d


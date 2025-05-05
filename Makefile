.PHONY:teardown setup
setup: teardown
	docker-compose -f stack.yml up --build -d

teardown:
	docker-compose -f stack.yml down -v
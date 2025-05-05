.PHONY:teardown setup
setup: teardown
	docker-compose -f stack.yml up -d

teardown:
	docker-compose -f stack.yml down -v
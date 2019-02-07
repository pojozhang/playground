ll
git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"
git clone git@github.com:pojozhang/playground-report.git
git add solutions/java/build/reports/tests/test/*
git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
git push

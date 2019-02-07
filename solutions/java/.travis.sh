git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

git clone git@github.com:pojozhang/playground-report.git
mkdir -p playground-report/java/
cp -r build/reports playground-report/java/reports
cd playground-report
git add *
git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
git push

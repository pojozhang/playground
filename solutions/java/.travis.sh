git config --global user.email "travis@travis-ci.org"
git config --global user.name "Travis CI"

git clone https://$GITHUB_TOKEN@github.com/pojozhang/playground-report
mkdir -p playground-report/java/
cp -r build/reports playground-report/java/reports
cd playground-report
git add *
git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
git push

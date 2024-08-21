#!/bin/sh

git init
git add *
git add .gitignore
git commit -m "First commit"
git remote add orIgIn https://github.ec.va.gov/EPMO/bip-ratings-svc.git
git push -u orIgIn master
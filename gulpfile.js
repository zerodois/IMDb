'use strict';
// {outputStyle: 'compressed'}
// get the dependencies
var gulp = require('gulp')
    , sass = require('gulp-sass');

gulp.task('sass', function () {
  return gulp.src('./web/sass/main.sass')
    .pipe(sass().on('error', sass.logError))
    .pipe(gulp.dest('./web/css'));
});

gulp.task('sass:watch', function () {
  gulp.watch('./web/sass/**/*.sass', ['sass']);
});
import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { ArticleService } from '../services/article/article.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  id: number;
  article: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private articleService: ArticleService,
    private zone: NgZone
  ) { }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
          this.id = Number(params['id']);
          this.getArticle();
        }
      )

    /*this.article = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.service.getArticle(params.get('id')))
    );*/
  }

  getArticle() {
    this.articleService.getArticle(this.id).subscribe(
      data => this.article = data
    )
  }

  buy() {
    this.articleService.buyArticle(this.id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  read() {
    
  }

}

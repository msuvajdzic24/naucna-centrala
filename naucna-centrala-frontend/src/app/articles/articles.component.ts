import { Component, OnInit, NgZone } from '@angular/core';
import { ArticleService } from '../services/article/article.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {

  articles: any;

  constructor(
    private articleService: ArticleService,
    private zone: NgZone
  ) { }

  ngOnInit() {
    this.getArticles();
  }

  getArticles() {
    this.articleService.getArticles().subscribe(
      data => this.articles = data
    )
  }

  buy(id: number) {
    this.articleService.buyArticle(id).subscribe(
      data => {
        this.zone.runOutsideAngular(() => {
          window.location.href = "" + data;
        });
      }
    )
  }

  read(id: number) {
    
  }

}

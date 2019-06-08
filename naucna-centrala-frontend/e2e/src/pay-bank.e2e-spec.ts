import { browser, element, by, protractor } from 'protractor';

describe('Edition compoment', () => {
    beforeAll(() => {
        browser.get('http://192.168.43.217:4201/');
        browser.driver.manage().window().maximize();

        let username = element(by.id('username'));
        expect(username.isDisplayed()).toBe(true);
        username.sendKeys('buyer1');

        let password = element(by.id('password'));
        expect(password.isDisplayed()).toBe(true);
        password.sendKeys('Bar5slova');

        let loginButton = element(by.buttonText('Login'));
        expect(loginButton.isDisplayed()).toBe(true);
        loginButton.click();
        expect(browser.getCurrentUrl()).toEqual('http://192.168.43.217:4201/home');
    });

    afterAll(() => {
        let logoutButton = element(by.buttonText('Logout'));
        expect(logoutButton.isDisplayed()).toBe(true);
        logoutButton.click();
        expect(browser.getCurrentUrl()).toEqual('http://192.168.43.217:4201/login');
    });

    it('should buy edition-success', () => {
        browser.get('http://192.168.43.217:4201/edition/5');

        let editionName = element.all(by.css('h2 b')).get(0);
        expect(editionName.getText()).toEqual("journal2 Edition 2");

        let buyButton = element(by.buttonText('Buy'));
        expect(buyButton.isDisplayed()).toBe(true);
        buyButton.click();

        // Koncetrator placanja
        // let bankPaymentButton = element.all(by.css('div.paymentMethod img.card')).get(0);
        let bankPaymentButton = element(by.id('Payment card'));

        // korisitit ovo ako implicitni wait nije dobro podesen
        browser.wait(function() { 
                return element(bankPaymentButton.isPresent());
            }, 
            5000
        );
        expect(bankPaymentButton.isDisplayed()).toBe(true);
        bankPaymentButton.click();

        // Banka
        let panInput = element(by.id('pan'));
        expect(panInput.isDisplayed()).toBe(true);
        panInput.sendKeys('5000001111111111111');

        let cardholderInput = element(by.id('cardholder'));
        expect(cardholderInput.isDisplayed()).toBe(true);
        cardholderInput.sendKeys('pera peric');

        let cvvInput = element(by.id('cvv'));
        expect(cvvInput.isDisplayed()).toBe(true);
        cvvInput.sendKeys('123');

        let monthInput = element(by.id('month'));
        expect(monthInput.isDisplayed()).toBe(true);
        monthInput.sendKeys('12');

        let yearInput = element(by.id('year'));
        expect(yearInput.isDisplayed()).toBe(true);
        yearInput.sendKeys('2019');

        let payButton = element(by.buttonText('Plati'));
        expect(payButton.isDisplayed()).toBe(true);
        payButton.click();

        // Konentrator placanja
        let redirectButton = element(by.buttonText('Redirect back'));
        expect(redirectButton.isDisplayed()).toBe(true);
        redirectButton.click();

        // Naucna centrala
        expect(browser.getCurrentUrl()).toEqual('http://192.168.43.217:4201/edition/5');

        let listButton = element(by.buttonText('List articles'));
        expect(listButton.isDisplayed()).toBe(true);
    });

    it('should buy edition-error', () => {
        browser.get('http://192.168.43.217:4201/edition/6');

        let editionName = element.all(by.css('h2 b')).get(0);
        expect(editionName.getText()).toEqual("journal2 Edition 3");

        let buyButton = element(by.buttonText('Buy'));
        expect(buyButton.isDisplayed()).toBe(true);
        buyButton.click();

        // Koncetrator placanja
        // let bankPaymentButton = element.all(by.css('div.paymentMethod img.card')).get(0);
        let bankPaymentButton = element(by.id('Payment card'));

        // korisitit ovo ako implicitni wait nije dobro podesen
        browser.wait(function() { 
                return element(bankPaymentButton.isPresent());
            }, 
            5000
        );
        expect(bankPaymentButton.isDisplayed()).toBe(true);
        bankPaymentButton.click();

        // Banka
        let panInput = element(by.id('pan'));
        expect(panInput.isDisplayed()).toBe(true);
        panInput.sendKeys('500000221111111111');

        let cardholderInput = element(by.id('cardholder'));
        expect(cardholderInput.isDisplayed()).toBe(true);
        cardholderInput.sendKeys('pera peric');

        let cvvInput = element(by.id('cvv'));
        expect(cvvInput.isDisplayed()).toBe(true);
        cvvInput.sendKeys('123');

        let monthInput = element(by.id('month'));
        expect(monthInput.isDisplayed()).toBe(true);
        monthInput.sendKeys('12');

        let yearInput = element(by.id('year'));
        expect(yearInput.isDisplayed()).toBe(true);
        yearInput.sendKeys('2019');

        let payButton = element(by.buttonText('Plati'));
        expect(payButton.isDisplayed()).toBe(true);
        payButton.click();

        // Konentrator placanja
        let redirectButton = element(by.buttonText('Redirect back'));
        expect(redirectButton.isDisplayed()).toBe(true);
        redirectButton.click();

        // Naucna centrala
        expect(browser.getCurrentUrl()).toEqual('http://192.168.43.217:4201/edition/6');

        let listButton = element(by.buttonText('Buy'));
        expect(listButton.isDisplayed()).toBe(true);
    });

    it('should buy edition-failed', () => {
        browser.get('http://192.168.43.217:4201/edition/6');

        let editionName = element.all(by.css('h2 b')).get(0);
        expect(editionName.getText()).toEqual("journal2 Edition 3");

        let buyButton = element(by.buttonText('Buy'));
        expect(buyButton.isDisplayed()).toBe(true);
        buyButton.click();

        // Koncetrator placanja
        // let bankPaymentButton = element.all(by.css('div.paymentMethod img.card')).get(0);
        let bankPaymentButton = element(by.id('Payment card'));

        // korisitit ovo ako implicitni wait nije dobro podesen
        browser.wait(function() { 
                return element(bankPaymentButton.isPresent());
            }, 
            5000
        );
        expect(bankPaymentButton.isDisplayed()).toBe(true);
        bankPaymentButton.click();

        // Banka
        let panInput = element(by.id('pan'));
        expect(panInput.isDisplayed()).toBe(true);
        panInput.sendKeys('5000001111111111111');

        let cardholderInput = element(by.id('cardholder'));
        expect(cardholderInput.isDisplayed()).toBe(true);
        cardholderInput.sendKeys('peraaa peric');

        let cvvInput = element(by.id('cvv'));
        expect(cvvInput.isDisplayed()).toBe(true);
        cvvInput.sendKeys('123');

        let monthInput = element(by.id('month'));
        expect(monthInput.isDisplayed()).toBe(true);
        monthInput.sendKeys('12');

        let yearInput = element(by.id('year'));
        expect(yearInput.isDisplayed()).toBe(true);
        yearInput.sendKeys('2019');

        let payButton = element(by.buttonText('Plati'));
        expect(payButton.isDisplayed()).toBe(true);
        payButton.click();

        // Konentrator placanja
        let redirectButton = element(by.buttonText('Redirect back'));
        expect(redirectButton.isDisplayed()).toBe(true);
        redirectButton.click();

        // Naucna centrala
        expect(browser.getCurrentUrl()).toEqual('http://192.168.43.217:4201/edition/6');

        let listButton = element(by.buttonText('Buy'));
        expect(listButton.isDisplayed()).toBe(true);
    });
});

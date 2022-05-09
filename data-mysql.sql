
use paymybuddy;

insert into user_account (id, firstname,lastname, login_mail, psswrd, solde) values 
(1,	"Max",	"Jacob",	"j@gmail.com",	"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2",	1200),
(2,	"Gustave",	"Caillebotte",	"c@gmail.com",	"$2a$10$5uEcL2Kn6I0ZurSun1Vzu.yxDJQjJsr0B7zKo/GgtGyMX18AAC7gi", 20),
(3, "Alexis",	"Axilette",	"a@gmail.com",	"$2a$10$ihxFsA4jcg2rzKg0szzxxezZNH2GdKeOo3PnOtYUx.mbbAcA1kzsq", 10),
(4, "François",	"Quesnel",	"q@gmail.com",	"$2a$10$XNECjKBIntOAxveNxikki.Dv/qCagCCU5Qb/dSbAC.4nsHn.BOJqa", 70),
(5, "Maurice",	"Utrillo",	"u@gmail.com",	"$2a$10$NS5VOwqQjTWYrRbEBCp/cekXxd.B5I3.9MgxV69nN/NoOOo.nbQ4e", 50),
(6, "admin", "admin", "admin@gmail.com", "$2a$10$Yut7ko9yB0pbMMg2GaTMqOp6UtMoyhYWVqdYLk9Rk7/SOkNUA.u/y", 0);

insert into connection (id, connection_id) values 
(1, 3),
(1, 2),
(1, 4),
(2, 3),
(4, 3),
(3, 2);

insert into bank_account (id, bank_name, iban, bic, login_mail) values
(1, "BNP", "123456789012345678901234567890", "BNP1234567890", "j@gmail.com"), 
(2, "BNP", "12345678901234567890", "BNP1234567890", "a@gmail.com"), 
(3, "CM", "123456789012345678901", "BNP1234567890", "c@gmail.com"), 
(4, "CM", "1234567890123456789012", "BNP1234567890", "admin@gmail.com");

insert into transac (id, amount, description, giver, receiver) values 
(1,	12,	"resto",	"j@gmail.com",	"a@gmail.com"),
(2,	20,	"cinema",	"j@gmail.com",	"a@gmail.com"),
(3,	30,	"restau",	"j@gmail.com",	"c@gmail.com"),
(4,	11,	"snack",	"j@gmail.com",	"q@gmail.com"),
(5,	5,	"ticket metro",	"c@gmail.com",	"a@gmail.com"),
(6,	65,	"cheque",	"q@gmail.com",	"a@gmail.com"),
(7,	8,	"ticket metro",	"a@gmail.com",	"c@gmail.com");

commit;



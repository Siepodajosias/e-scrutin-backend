package com.escrutin.escrutinbackend.controller;

import com.escrutin.escrutinbackend.controller.dto.NotificationMessage;
import com.escrutin.escrutinbackend.enums.TypeNotification;
import com.escrutin.escrutinbackend.facade.CommissionLocaleFacade;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
	private final String SEPARATOR = "_";
	private final SimpMessagingTemplate brokerMessagingTemplate;
	private final CommissionLocaleFacade commissionLocaleFacade;

	public NotificationController(SimpMessagingTemplate brokerMessagingTemplate,
								  CommissionLocaleFacade commissionLocaleFacade) {
		this.brokerMessagingTemplate = brokerMessagingTemplate;
		this.commissionLocaleFacade = commissionLocaleFacade;
	}

//	@MessageMapping("/traitement-fichier")
//	@SendTo("/topic/notifier-traitement-fichier")
//	public NotificationMessage notifier(String message) throws Exception {
//		Thread.sleep(5000); // simulated delay
//		return new NotificationMessage(TypeNotification.LECTURE_FICHIER, message);
//	}

	/**
	 * Déclenche une notification de traitement de fichier.
	 *
	 * @param message le message.
	 */
	@GetMapping("/api/traitement-termine")
	public void notifierTraitementFichier(String message) {
		String codeCommissionLocale = message.split(SEPARATOR)[0];
		NotificationMessage notificationMessage = commissionLocaleFacade.recupererInformations(codeCommissionLocale);
		this.brokerMessagingTemplate.convertAndSend(
				"/topic/notifier-traitement-fichier",
				notificationMessage
		);
	}
}